package com.kaolee.hotel.utils.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tokens") // Define o nome da coleção no MongoDB
public class Token {

    @Id
    private String id; // MongoDB usa String para IDs gerados automaticamente.

    @Indexed(unique = true) // Define como um campo único.
    private String token;

    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @Field("user_id") // Relacionamento manual usando o ID do usuário.
    private String userId; // MongoDB não suporta referências automáticas como em JPA.
}
