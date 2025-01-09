package jdbc;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Order {
    private Long id;
    @NonNull
    private String name;
    private String number;
    private List<OrderRow> orderRows = new ArrayList<>();
}
