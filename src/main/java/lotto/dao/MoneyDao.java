package lotto.dao;

import com.google.gson.Gson;
import lotto.DataBase;
import lotto.domain.Lotto;
import lotto.domain.Money;
import lotto.domain.Number;
import lotto.domain.WinningLotto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyDao {
    private final DataBase dataBase;

    public MoneyDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public int addMoney(Money money, int times) throws Exception {
        String query = "INSERT INTO money (times, money) VALUES (?, ?) ON DUPLICATE KEY UPDATE times = ?, money = money + ?";
        PreparedStatement pstmt = dataBase.getConnection().prepareStatement(query);

        pstmt.setInt(1, times + 1);
        pstmt.setInt(2, money.getMoney());
        pstmt.setInt(3, times + 1);
        pstmt.setInt(4, money.getMoney());

        return pstmt.executeUpdate();
    }

    public Money findByTimes(int times) throws SQLException {
        String query = "SELECT * FROM money WHERE times = ?";
        PreparedStatement pstmt = dataBase.getConnection().prepareStatement(query);
        pstmt.setInt(1, times);
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) return null;

        return new Money(rs.getInt("money"));
    }
}
