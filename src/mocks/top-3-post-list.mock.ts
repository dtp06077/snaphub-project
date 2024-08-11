import { PostListItem } from "../types/interface";

const top3PostListMock: PostListItem[] = [
    {
        postId: 1,
        title: "안녕하세요",
        content: "반갑습니다. 저의 이름은 김희성 입니다",
        postTitleImage: "data:image/jpeg;base64,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",
        postDateTime: "2023. 08. 08 00:12:12",
        posterName: "huiseong",
        posterProfileImage: null,
        emotionCount: 0,
        commentCount: 0,
        viewCount: 0
    },
    {
        postId: 1,
        title: "안녕하세요",
        content: "반갑습니다. 저의 이름은 김희성 입니다",
        postTitleImage: null,
        postDateTime: "2023. 08. 08 00:12:12",
        posterName: "huiseong",
        posterProfileImage: null,
        emotionCount: 0,
        commentCount: 0,
        viewCount: 0
    },
    {
        postId: 1,
        title: "안녕하세요",
        content: "반갑습니다. 저의 이름은 김희성 입니다",
        postTitleImage: null,
        postDateTime: "2023. 08. 08 00:12:12",
        posterName: "huiseong",
        posterProfileImage: null,
        emotionCount: 0,
        commentCount: 0,
        viewCount: 0
    }
];

export default top3PostListMock;