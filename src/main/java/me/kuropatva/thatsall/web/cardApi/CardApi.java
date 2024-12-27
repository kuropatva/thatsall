package me.kuropatva.thatsall.web.cardApi;

import me.kuropatva.thatsall.model.cards.CardDataTable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cardsapi")
@SuppressWarnings("unused")
public class CardApi {

    @GetMapping("/name/{id}")
    public String getName(@PathVariable String id) {
        return CardDataTable.get(id).name();
    }

    @GetMapping("/desc/{id}")
    public String getDesc(@PathVariable String id) {
        return CardDataTable.get(id).description();
    }

    @GetMapping("/cost/{id}")
    public String getCost(@PathVariable String id) {
        return String.valueOf(CardDataTable.get(id).cost());
    }

    @GetMapping("/json/{id}")
    public String getJson(@PathVariable String id) {
        return CardDataTable.get(id).toJson();
    }
}
