package hello.itemservice.domain.item;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

@Data
//@ScriptAssert(lang="javascript", script="_this.price * _this.quantity >= 10000",message = "10000원 넘게 입력해주세요")
//기능적으로 사용하기 불편함 그냥 자바 코드로 validation하는게 더 편한듯
public class Item {

//    @NotNull(groups = UpdateCheck.class)
    private Long id;
//    @NotBlank(groups = {SaveCheck.class,UpdateCheck.class})
    private String itemName;
//    @NotNull(groups = {SaveCheck.class,UpdateCheck.class})
//    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class,UpdateCheck.class})
    private Integer price;
//    @NotNull(groups = {SaveCheck.class,UpdateCheck.class})
//    @Max(value = 9999,groups = SaveCheck.class)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
