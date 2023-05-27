package Game.AdatB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result extends ArrayList<Result> {

    String jatekosEgy;

    String jatekosKetto;

    String nyertes;
}
