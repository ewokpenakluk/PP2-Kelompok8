package model;

import java.time.LocalDateTime;

public class HistoryPenjemputanModel {
    private Integer idHistory;
    private Integer idMasyarakat;
    private Integer idKurir;
    private Integer idSampah;
    private Integer idLokasi;
    private Integer idDropbox;
    private LocalDateTime tanggalPenjemputan;
    private Float totalBerat;
    private Integer totalPoin;
    private Integer jumlahPenjemputan;

    private MasyarakatModel masyarakat;
    private KurirModel kurir;
    private SampahModel sampah;
    private LokasiModel lokasi;
    private DropBoxModel dropBox;
    private KategoriModel kategori;

    public Integer getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Integer idHistory) {
        this.idHistory = idHistory;
    }

    public Integer getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(Integer idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public Integer getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(Integer idKurir) {
        this.idKurir = idKurir;
    }

    public Integer getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(Integer idSampah) {
        this.idSampah = idSampah;
    }

    public Integer getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(Integer idLokasi) {
        this.idLokasi = idLokasi;
    }

    public Integer getIdDropbox() {
        return idDropbox;
    }

    public void setIdDropbox(Integer idDropbox) {
        this.idDropbox = idDropbox;
    }

    public LocalDateTime getTanggalPenjemputan() {
        return tanggalPenjemputan;
    }

    public void setTanggalPenjemputan(LocalDateTime tanggalPenjemputan) {
        this.tanggalPenjemputan = tanggalPenjemputan;
    }

    public Float getTotalBerat() {
        return totalBerat;
    }

    public void setTotalBerat(Float totalBerat) {
        this.totalBerat = totalBerat;
    }

    public Integer getTotalPoin() {
        return totalPoin;
    }

    public void setTotalPoin(Integer totalPoin) {
        this.totalPoin = totalPoin;
    }

    public Integer getJumlahPenjemputan() {
        return jumlahPenjemputan;
    }

    public void setJumlahPenjemputan(Integer jumlahPenjemputan) {
        this.jumlahPenjemputan = jumlahPenjemputan;
    }

    public MasyarakatModel getMasyarakat() {
        return masyarakat;
    }

    public void setMasyarakat(MasyarakatModel masyarakat) {
        this.masyarakat = masyarakat;
    }

    public KurirModel getKurir() {
        return kurir;
    }

    public void setKurir(KurirModel kurir) {
        this.kurir = kurir;
    }

    public SampahModel getSampah() {
        return sampah;
    }

    public void setSampah(SampahModel sampah) {
        this.sampah = sampah;
    }

    public LokasiModel getLokasi() {
        return lokasi;
    }

    public void setLokasi(LokasiModel lokasi) {
        this.lokasi = lokasi;
    }

    public DropBoxModel getDropBox() {
        return dropBox;
    }

    public void setDropBox(DropBoxModel dropBox) {
        this.dropBox = dropBox;
    }

    public KategoriModel getKategori() {
        return kategori;
    }

    public void setKategori(KategoriModel kategori) {
        this.kategori = kategori;
    }
}