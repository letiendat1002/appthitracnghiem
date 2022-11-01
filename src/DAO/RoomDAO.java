package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public static List<Room> selectAll() {
        var list = new ArrayList<Room>();
        var query = "select * from rooms";
        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(
                        new Room(
                                resultSet.getLong("room_id"),
                                resultSet.getLong("exam_id"),
                                resultSet.getString("title"),
                                resultSet.getInt("time_limit"),
                                resultSet.getString("password"),
                                resultSet.getBoolean("is_available")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static Room selectVerifiedRoom(String roomID, String password) {
        var room = new Room();
        var query = "select * from rooms where room_id=? and password=?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setString(1, roomID);
            ps.setString(2, password);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                room.setRoom_id(resultSet.getLong("room_id"));
                room.setExam_id(resultSet.getLong("exam_id"));
                room.setTitle(resultSet.getString("title"));
                room.setTime_limit(resultSet.getInt("time_limit"));
                room.setPassword(resultSet.getString("password"));
                room.setAvailable(resultSet.getBoolean("is_available"));
                return room;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Room selectByID(long roomID) {
        var room = new Room();
        var query = "select * from rooms where room_id=?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, roomID);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                room.setRoom_id(resultSet.getLong("room_id"));
                room.setExam_id(resultSet.getLong("exam_id"));
                room.setTitle(resultSet.getString("title"));
                room.setTime_limit(resultSet.getInt("time_limit"));
                room.setPassword(resultSet.getString("password"));
                room.setAvailable(resultSet.getBoolean("is_available"));
                return room;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean insert(Room room) {
        var query = "insert into rooms(exam_id,title,time_limit,password,is_available) values(?,?,?,?,?)";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, room.getExam_id());
            ps.setString(2, room.getTitle());
            ps.setInt(3, room.getTime_limit());
            ps.setString(4, room.getPassword());
            ps.setBoolean(5, room.isAvailable());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(Room room) {
        var query = "update rooms set exam_id = ?, title = ?, time_limit = ?, password = ?, is_available = ? where room_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, room.getExam_id());
            ps.setString(2, room.getTitle());
            ps.setInt(3, room.getTime_limit());
            ps.setString(4, room.getPassword());
            ps.setBoolean(5, room.isAvailable());
            ps.setLong(6, room.getRoom_id());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(long room_id) {
        var query = "delete from rooms where room_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, room_id);
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Room> rooms = RoomDAO.selectAll();
        System.out.println(rooms.get(0).getTitle());
        Room room = RoomDAO.selectByID(1);
        System.out.println(
                (room != null ? room.getTime_limit() : -1)
                        + " "
                        + (room != null ? room.getTitle() : "empty")
        );
        assert room != null;
        room.setPassword("123456");
        System.out.println(RoomDAO.update(room));
    }
}
