package com.example.ajie_es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Document(indexName = "law")
public class Law {

    String title;
    String office;

    @Field(type = FieldType.Date)
    String publish;

    @Field(type = FieldType.Date_Nanos)
    String expiry;

}
