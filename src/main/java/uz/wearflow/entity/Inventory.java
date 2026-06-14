package uz.wearflow.entity;

import jakarta.persistence.*;

// @Entity - bu sinf bazadagi "inventory" jadvaliga mos keladi
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mahsulot_nomi", nullable = false)
    private String mahsulotNomi;

    @Column(nullable = false)
    private Integer miqdori;

    // Omborning turi, masalan: "Asosiy Ombor", "Toshkent Filiali"
    @Column(name = "ombor_turi", nullable = false)
    private String omborTuri;

    // --- Getter va Setter metodlari ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMahsulotNomi() { return mahsulotNomi; }
    public void setMahsulotNomi(String mahsulotNomi) { this.mahsulotNomi = mahsulotNomi; }

    public Integer getMiqdori() { return miqdori; }
    public void setMiqdori(Integer miqdori) { this.miqdori = miqdori; }

    public String getOmborTuri() { return omborTuri; }
    public void setOmborTuri(String omborTuri) { this.omborTuri = omborTuri; }
}
