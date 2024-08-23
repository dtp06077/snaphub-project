export default interface JoinRequestDto {
    loginId: string;
    password: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string | null;
    address: string | null;
    addressDetail: string | null;
    agreedPersonal: boolean;
}