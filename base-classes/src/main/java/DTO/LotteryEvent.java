package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotteryEvent {

    private LocalDateTime playedAt;
    private String pesel;
    private boolean result;

}
