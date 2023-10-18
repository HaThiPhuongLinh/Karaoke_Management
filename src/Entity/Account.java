package Entity;

import DAOs.AccountDAO;
import DAOs.TypeOfServiceDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    private String taiKhoan;
    private String matKhau;
    private Boolean tinhTrang;

    public Account(ResultSet rs) throws SQLException {
        this(rs.getString("taiKhoan"), rs.getString("matKhau"), rs.getBoolean("tinhTrang"));
    }

    public Account(String taiKhoan, String matKhau, Boolean tinhTrang) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
    }

    public Account(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public Account() {
        this.taiKhoan = "";
        this.matKhau = "";
        this.tinhTrang = false;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Boolean getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(Boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "Account{" +
                "taiKhoan='" + taiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", tinhTrang=" + tinhTrang +
                '}';
    }
}
