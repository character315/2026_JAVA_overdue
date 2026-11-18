package Pomodoro;

public interface TimerListener {

    // 남은 시간 변경 알림
    void onTimeChanged(int remainingSeconds);

    // 집중 및 휴식 모드 변경 알림
    void onModeChanged(boolean isBreak);

    // 타이머 실행 상태 변경 알림
    void onRunningChanged(boolean isRunning);
}
