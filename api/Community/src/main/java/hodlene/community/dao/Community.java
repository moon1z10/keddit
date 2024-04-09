package hodlene.community.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class Community {
    private String id;
    private String icon;
    private String title;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    // Lombok @Data generates getters and setters
}
