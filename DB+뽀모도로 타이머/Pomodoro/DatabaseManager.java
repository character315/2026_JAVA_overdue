package Pomodoro;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:pomodoro.db";

    public static void main(String[] args) {

        // 학습 기록 테이블 생성
        String sql = """
            CREATE TABLE IF NOT EXISTS focus_sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                completed_at TEXT DEFAULT CURRENT_TIMESTAMP,
                duration_seconds INTEGER NOT NULL
            )
            """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

        	stmt.executeUpdate(sql);
        	System.out.println("데이터베이스 생성 완료!");

        	// saveFocusSession(30);
        	showStatistics();
        	showHistory();
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

    // 집중 기록 저장
    public static void saveFocusSession(int durationSeconds) {

        String sql = "INSERT INTO focus_sessions (duration_seconds) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, durationSeconds);
            pstmt.executeUpdate();

            System.out.println("집중 기록 저장 완료!");

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

    // 집중 기록 통계
    public static void showStatistics() {

        String sql = "SELECT COUNT(*) AS count, " +
                     "COALESCE(SUM(duration_seconds), 0) AS total " +
                     "FROM focus_sessions";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("완료한 집중 횟수: "
                        + rs.getInt("count") + "회");

                System.out.println("총 집중 시간: "
                        + rs.getInt("total") + "초");
            }

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

    // 데이터베이스 자동 초기화
    public static void initializeDatabase() {

        String sql = """
            CREATE TABLE IF NOT EXISTS focus_sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                completed_at TEXT DEFAULT CURRENT_TIMESTAMP,
                duration_seconds INTEGER NOT NULL
            )
            """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("DB 연결 완료!");

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

    // 학습 기록 조회
    public static void showHistory() {

        String sql = """
            SELECT id,
                   datetime(completed_at, 'localtime') AS local_time,
                   duration_seconds
            FROM focus_sessions
            ORDER BY id DESC
            """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== 학습 기록 ===");

            while (rs.next()) {

                System.out.println(
                    "번호: " + rs.getInt("id") +
                    " | 완료 시간: " + rs.getString("local_time") +
                    " | 집중 시간: " +
                    rs.getInt("duration_seconds") + "초"
                );
            }

        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }




}
