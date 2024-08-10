export default interface CommentListItem {
    loginId: string;
    name: string;
    profileImage: string | null;
    commentDateTime: string;
    content: string;
}