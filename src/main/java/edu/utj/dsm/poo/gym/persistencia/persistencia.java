package edu.utj.dsm.poo.gym.persistencia;

import edu.utj.dsm.poo.gym.modelo.Administrador;

public class persistencia {

    public static void main(String[] args) {
        // 1. Instanciar el gestor de persistencia
        AdministradorArrayList dao = new AdministradorArrayList();

        // 2. Crear administradores de prueba
        Administrador admin1 = new Administrador();
        admin1.setRfc("ADM123456789");
        admin1.setNombre("Carlos");
        admin1.setPrimerApellido("Pérez");

        Administrador admin2 = new Administrador();
        admin2.setRfc("ADM987654321");
        admin2.setNombre("Ana");
        admin2.setPrimerApellido("Gómez");

        // 3. Probar agregar
        dao.agregar(admin1);
        dao.agregar(admin2);

        // 4. Probar listar e imprimir en consola
        System.out.println("=== ADMINISTRADORES EN MEMORIA ===");
        for (Administrador admin : dao.listar()) {
            System.out.println("RFC: " + admin.getRfc() + " | Nombre: " + admin.getNombre() + " " + admin.getPrimerApellido());
        }
    }
}