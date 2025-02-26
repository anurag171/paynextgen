package com.anurag.temporal.payment.processor.event.warmup;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        path = "/warmup",
        consumes = APPLICATION_JSON_VALUE
)
public class PaymentWarmUpController {

    @PostMapping
    public ResponseEntity<String> post(@RequestBody @Valid PaymentWarmUpRequestDto warmUpRequestDto) {
        return ResponseEntity.ok(UUID.randomUUID().toString() + warmUpRequestDto);
    }
}