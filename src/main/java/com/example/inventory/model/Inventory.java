package com.example.inventory.model;

import com.example.inventory.controller.MainPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    static {
        // -- Part Table --
        Part partOne = new InHouse(MainPanel.partIndexCounter++, "In-House Part", 1.00, 10, 1, 100, 1);
        Part partTwo = new InHouse(MainPanel.partIndexCounter++, "In-House Part", 2.00, 20, 2, 200, 2);
        Part partThree = new Outsourced(MainPanel.partIndexCounter++, "Outsourced Part", 3.00, 30, 3, 300, "Cee");
        Part partFour = new Outsourced(MainPanel.partIndexCounter++, "Outsourced Part", 4.00, 40, 4, 400, "Dee");
        Inventory.addPart(partOne);
        Inventory.addPart(partTwo);
        Inventory.addPart(partThree);
        Inventory.addPart(partFour);


        // -- Product Table --
        Product productOne = new Product(MainPanel.productIndexCounter++, "Product One", 1.00, 10, 1, 100);
        Product productTwo = new Product(MainPanel.productIndexCounter++, "Product Two", 2.00, 20, 2, 200);
        Product productThree = new Product(MainPanel.productIndexCounter++, "Product Three", 3.00, 30, 3, 300);
        Product productFour = new Product(MainPanel.productIndexCounter++, "Product Four", 4.00, 40, 4, 400);
        Inventory.addProduct(productOne);
        Inventory.addProduct(productTwo);
        Inventory.addProduct(productThree);
        Inventory.addProduct(productFour);
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains((partName))) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * @return list of associative parts
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains((productName))) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * @param index is searched in the inventory
     * @param selectedPart is searched in the inventory
     */
    public static void updatePart(int index, Part selectedPart) {
        for (Part part : allParts) {
            if (part.getId() == index) {
                allParts.set(allParts.indexOf(part), selectedPart);
            }
        }
    }

    /**
     * @param index is searched in the inventory
     * @param selectedProduct is searched in the inventory
     */
    public static void updateProduct(int index, Product selectedProduct) {
        for (Product product : allProducts) {
            if (product.getId() == index) {
                allProducts.set(allProducts.indexOf(product), selectedProduct);
            }
        }
    }

    /**
     * @return true if the part is successfully deleted
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * @return true if the product is successfully deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

