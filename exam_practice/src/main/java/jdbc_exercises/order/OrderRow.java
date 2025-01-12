package jdbc_exercises.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRow {
    private String itemName;
    private Object price;
}
