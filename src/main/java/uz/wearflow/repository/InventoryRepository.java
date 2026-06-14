package uz.wearflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.wearflow.entity.Inventory;

// Inventory uchun repository. Long - bu id ning tipi.
// findAll(), findById(), save(), deleteById() metodlari avtomatik mavjud.
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Omborlar ro'yxatini ombor turiga qarab filtrlash uchun
    // Spring avtomatik: SELECT * FROM inventory WHERE ombor_turi = ?
    java.util.List<Inventory> findByOmborTuri(String omborTuri);
}
