export default interface JoinRequestDto {
    loginId: string;
    password: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string;
    address: string | null;
    agreedPersonal: boolean;
}