package model;

public class SampahModel {
    private Integer idSampah;
    private Integer idKategori;
    private String namaSampah;
    private Float berat;
    private Integer poin;
    private KategoriModel kategori;

    public Integer getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(Integer idSampah) {
        this.idSampah = idSampah;
    }

    public Integer getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(Integer idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaSampah() {
        return namaSampah;
    }

    public void setNamaSampah(String namaSampah) {
        this.namaSampah = namaSampah;
    }

    public Float getBerat() {
        return berat;
    }

    public void setBerat(Float berat) {
        this.berat = berat;
    }

    public Integer getPoin() {
        return poin;
    }

    public void setPoin(Integer poin) {
        this.poin = poin;
    }

    public KategoriModel getKategori() {
        return kategori;
    }

    public void setKategori(KategoriModel kategori) {
        this.kategori = kategori;
    }
}