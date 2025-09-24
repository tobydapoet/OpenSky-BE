package com.example.OpenSky.requests.FeedBack;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedBackRequest {
    private TableType tableType;

    private String tableId;

    private Integer rate ;

    private String description;

    private String billId;
}
