export default interface PostListItem {
    postId: number;
    title: string;
    content: string;
    postTitleImage: string | null;
    postDateTime: string;
    posterName: string;
    posterProfileImage: string | null;
    emotionCount: number;
    commentCount: number;
    viewCount: number;
}