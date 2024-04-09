package hodlene.community.service;

import hodlene.community.config.DynamoDBConfig;
import hodlene.community.dao.Community;
import hodlene.community.dao.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {
    private final DynamoDbEnhancedClient enhancedClient;

    @Autowired
    public CommunityServiceImpl(DynamoDBConfig dbConfig) {
        this.enhancedClient = dbConfig.dynamoDbEnhancedClient();
    }

    @Override
    public List<Community> getCommunityList() {
        DynamoDbTable<Community> table = enhancedClient.table("Community", TableSchema.fromBean(Community.class));
        // 스캔 요청 생성
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder().build();
        // 스캔 실행

        return table.scan(scanRequest)
                .items()
                .stream()
                .sorted(Comparator.comparingInt(c -> Integer.parseInt(c.getId())))
                .toList();
    }

    @Override
    public List<Post> getRecentPosts(String communityId) {
        DynamoDbTable<Post> postTable = enhancedClient.table("Posts", TableSchema.fromBean(Post.class));

        Expression.Builder expBuilder = Expression.builder();
        // 0 : All
        // Others : Filter by community id
        if (!communityId.equals("0")) {
            expBuilder.expression("communityId = :id")
                    .expressionValues(Map.of(":id", AttributeValue.builder().s(communityId).build()));
        }
        Expression exp = expBuilder.build();

        // 스캔 요청 생성
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(exp)
                .limit(100)
                .build();

        // 스캔 실행
        List<Post> posts = postTable.scan(scanRequest)
                .items()
                .stream()
                .toList();

        // Vote를 기준으로 정렬 후 상위 10개 반환
        return posts.stream()
                .sorted((p1, p2) -> p2.getVote().compareTo(p1.getVote()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getPostsByCommunity(String communityId) {
        return null;
    }

    @Override
    public Post createPost(Post post) {
        DynamoDbTable<Post> postTable = enhancedClient.table("Posts", TableSchema.fromBean(Post.class));

        // Post 객체에 ID 및 datetime 설정
        post.setId(UUID.randomUUID().toString()); // Unique ID 생성
        String datetimeStr = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        post.setDatetime(datetimeStr); // 현재 시각 설정

        // DynamoDB에 Post 객체 삽입
        postTable.putItem(post);

        // 삽입된 Post 객체 반환
        return post;
    }

    @Override
    public Post updatePost(String postId, Post post) {
        return null;
    }

    @Override
    public void deletePost(String postId) {

    }

    @Override
    public void init(List<Community> l) {
        DynamoDbTable<Community> table = enhancedClient.table("Community", TableSchema.fromBean(Community.class));

        // 리스트 내 모든 Community 객체를 삽입
        l.forEach(table::putItem);
    }
}
