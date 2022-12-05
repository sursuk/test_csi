import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Cost {
    private long id;  //идентификатор в БД
    private String product_code; //код товара
    private int number; // номер цены
    private int depart; // номер отдела
    private LocalDateTime begin; // начало действия (заменить на LocalDateTime)
    private LocalDateTime end; // конец действия
    private long value; // значение цены в копейках

    public Cost(Cost other) {
        this(other.getId(), other.getProduct_code(), other.getNumber(), other.getDepart(),
                other.getBegin(), other.getEnd(), other.getValue());
    }
}
