export default interface UserInfo {
    userId: number;
    loginId: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string;
    address: string | null;
    roles: string[];
}