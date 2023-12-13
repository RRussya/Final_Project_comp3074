package ca.georgebrown.comp3074.uiprototype;

public class MenuModel {

    private int id;
    private String category;
    private String name;
    private double price;

    private boolean isSelected;

    public MenuModel(int id, String category, String name, double price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "Category='" + category + '\'' +
                ", Name='" + name + '\'' +
                ", Price=" + price +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
