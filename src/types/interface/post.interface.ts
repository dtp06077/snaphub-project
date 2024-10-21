export default interface Post {
    postId: number;
    title: string;
    content: string;
    imageList: string[];
    postDateTime: string;
    posterName: string;
    posterId: number;
    posterLoginId: string;
    posterProfileImage: string|null;
}
