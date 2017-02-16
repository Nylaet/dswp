
package net.wildpark.dswp.enums;

public enum DataType {
    TEMP("C"),HUM("%"),PRES("мм рт.ст."),STATE("");
    private String unit;

    private DataType(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }    
    
}
