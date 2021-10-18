
package com.example.ajie_es.pojo;


        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import org.springframework.data.annotation.Id;
        import org.springframework.data.elasticsearch.annotations.Document;
        import org.springframework.data.elasticsearch.annotations.Field;
        import org.springframework.data.elasticsearch.annotations.FieldType;

        import java.lang.annotation.Documented;
        import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "sentence")
public class Sentence {
    @Id
    private  String id_;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private  String describe;

}

