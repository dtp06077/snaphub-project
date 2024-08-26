import ResponseDto from "../response.dto";

export default interface UserInfoResponseDto extends ResponseDto {
    userId: number;
    loginId: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string | null;
    address: string | null;
    addressDetail: string | null;
    roles: string[];
}