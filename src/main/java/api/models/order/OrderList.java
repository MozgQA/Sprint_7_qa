package api.models.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderList {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<AvailableStation> availableStations;
}
