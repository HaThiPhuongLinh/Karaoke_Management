package Entity;

public class Account {
    private String taiKhoan;
    private String matKhau;
    private String tinhTrang;

    public Account(String taiKhoan, String matKhau, String tinhTrang) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
    }

    public Account() {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }
}
