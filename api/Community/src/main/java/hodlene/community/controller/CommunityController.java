package hodlene.community.controller;

import hodlene.community.dao.Community;
import hodlene.community.service.CommunityService;
import hodlene.community.dao.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Community>> getRecentPosts() {
        return ResponseEntity.ok(communityService.getCommunityList());
    }

    @GetMapping("/recentPosts/{communityId}")
    public ResponseEntity<List<Post>> getRecentPosts(@PathVariable("communityId") String communityId) {
        List<Post> topPosts = communityService.getRecentPosts(communityId);
        return ResponseEntity.ok(topPosts);
    }

    @PostMapping("/post")
    public Post post(@RequestBody Map<String, String> queryParameter) {
        Post newPost = Post.builder()
                .authorId(queryParameter.get("author"))
                .subject(queryParameter.get("subject"))
                .context(queryParameter.get("context"))
                .vote(0)
                .build();
        return communityService.createPost(newPost);
    }

    @GetMapping("/init")
    public void init() {
        java.util.List<hodlene.community.dao.Community> l = new java.util.ArrayList<>();
        for(var i = 1; i <= 30; i++) {
            String t = switch (i) {
                case 1 -> "게임";
                case 2 -> "스포츠";
                case 3 -> "Business";
                case 4 -> "암호화폐";
                case 5 -> "Television";
                case 6 -> "Celebrity";
                case 7 -> "애완동물";
                case 8 -> "애니메이션";
                case 9 -> "예술";
                case 10 -> "자동차";
                case 11 -> "DIY";
                case 12 -> "PC";
                case 13 -> "철학";
                case 14 -> "패션";
                case 15 -> "음식";
                case 16 -> "역사";
                case 17 -> "취미";
                case 18 -> "법률";
                case 19 -> "교육";
                case 20 -> "군대";
                case 21 -> "영화";
                case 22 -> "음악";
                case 23 -> "스트리머";
                case 24 -> "정치";
                case 25 -> "IT";
                case 26 -> "문학";
                case 27 -> "종교";
                case 28 -> "과학";
                case 29 -> "기술";
                case 30 -> "여행";
                default -> "";
            };

            hodlene.community.dao.Community c = hodlene.community.dao.Community.builder()
                    .id(""+i)
                    .icon("bi-braces")
                    .title(t)
                    .build();

            l.add(c);
        }
        communityService.init(l);
    }
}