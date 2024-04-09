package hodlene.community.service;

import hodlene.community.dao.Community;
import hodlene.community.dao.Post;

import java.util.List;

public interface CommunityService {
    List<Community> getCommunityList();
    List<Post> getRecentPosts(String communityId);
    List<Post> getPostsByCommunity(String communityId);
    Post createPost(Post post);
    Post updatePost(String postId, Post post);
    void deletePost(String postId);

    void init(List<Community> l);
}
