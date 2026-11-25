package Pomodoro;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.function.Consumer;
import javax.swing.*;

public class PomodoroTimer {
	// 테스트용: 집중 30초 / 휴식 5초
	private static final int FOCUS = 30, BREAK = 5;
    private static int seconds = FOCUS;
    private static boolean running, paused, resting;
    private static volatile TimerListener listener;

    public static void setTimerListener(TimerListener l) { listener = l; }

    public static void main(String[] args) {
    	DatabaseManager.initializeDatabase();
        // 실제 GUI 연결 전 PET 알림 확인용
        setTimerListener(new TimerListener() {
            public void onTimeChanged(int s) { }
            public void onRunningChanged(boolean r) { }
            public void onModeChanged(boolean b) {
                System.out.println(b ? "[PET] 휴식 모드" : "[PET] 집중 모드");
            }
        });

        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(PomodoroTimer::tick, 1, 1, TimeUnit.SECONDS);
        System.out.println("=== 뽀모도로 타이머 ===");
        System.out.println("명령어: start / pause / resume / reset / exit");
        printTime();

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                if (command(sc.nextLine().trim().toLowerCase())) break;
            }
        } finally { timer.shutdownNow(); }
    }

    // 명령어 처리
    private static synchronized boolean command(String c) {
        switch (c) {
            case "start" -> {
                if (!running && !paused) {
                    running = true; notifyRunning();
                    System.out.println("타이머 시작!");
                } else System.out.println("이미 시작된 타이머입니다.");
            }
            case "pause" -> {
                if (running) {
                    running = false; paused = true; notifyRunning();
                    System.out.println("타이머 일시정지!");
                } else System.out.println("실행 중인 타이머가 없습니다.");
            }
            case "resume" -> {
                if (paused) {
                    running = true; paused = false; notifyRunning();
                    System.out.println("타이머 재개!");
                } else System.out.println("일시정지된 타이머가 없습니다.");
            }
            case "reset" -> {
                running = paused = resting = false;
                seconds = FOCUS;
                notifyMode(); notifyRunning();
                System.out.println("타이머 초기화!");
                printTime();
            }
            case "exit" -> { System.out.println("프로그램 종료!"); return true; }
            default -> System.out.println("올바른 명령어를 입력하세요.");
        }
        return false;
    }

    // 1초마다 카운트다운
    private static synchronized void tick() {
        if (!running) return;
        seconds--;
        printTime();
        if (seconds > 0) return;

        String msg = resting ? "휴식 시간이 종료되었습니다!" : "집중 시간이 종료되었습니다!";
        System.out.println(msg);
        showAlert(msg);
        if (!resting) {
            DatabaseManager.saveFocusSession(FOCUS);

            System.out.println("휴식 타이머 시작!");
            resting = true;
            seconds = BREAK;
            notifyMode();
            printTime();
        }
            running = paused = resting = false;
            seconds = FOCUS;
            notifyMode(); notifyRunning(); notifyTime();
            System.out.println("다시 시작하려면 start를 입력하세요.");
        }
    

    private static void printTime() {
        System.out.printf("%02d:%02d%n", seconds / 60, seconds % 60);
        notifyTime();
    }

    // GUI/캐릭터 리스너 알림
    private static void send(Consumer<TimerListener> action) {
        TimerListener l = listener;
        if (l != null) SwingUtilities.invokeLater(() -> action.accept(l));
    }
    private static void notifyTime() {
        int s = seconds;
        send(l -> l.onTimeChanged(s));
    }
    private static void notifyMode() {
        boolean b = resting;
        send(l -> l.onModeChanged(b));
    }
    private static void notifyRunning() {
        boolean r = running;
        send(l -> l.onRunningChanged(r));
    }
    private static void showAlert(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                null, msg, "뽀모도로 타이머", JOptionPane.INFORMATION_MESSAGE));
    }
}


