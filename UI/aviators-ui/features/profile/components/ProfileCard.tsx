import { logoutUser } from "@/features/auth";
import { useAppDispatch, useAppSelector } from "@/lib/store/hooks";

export function ProfileCard() {
  const dispatch = useAppDispatch();
  const { user } = useAppSelector((state) => state.auth);

  const handleLogout = async () => {
    await dispatch(logoutUser());
  };

  return (
    <div
      id="profile-card"
      className="min-w-2xs flex flex-col items-center py-2 px-3"
    >
      <div
        id="profile-row"
        className="flex items-center py-2 justify-start w-full cursor-pointer"
      >
        <div className="w-14 h-14 rounded-full bg-secondary border border-border text-secondary-foreground flex items-center justify-center cursor-pointer">
          <span className="text-sm font-semibold">AD</span>
        </div>
        <div id="profile-detail" className="flex flex-col ml-4">
          <p className="text-md text-secondary-foreground hover:text-foreground transition">
            {user?.name}
          </p>
          <p className="text-xs text-secondary-foreground underline hover:text-foreground transition">
            View profile
          </p>
        </div>
      </div>

      <div
        id="profile-utility"
        className="flex-col items-center w-full text-secondary-foreground ml-2 text-md"
      >
        <div className="py-2 items-center justify-center cursor-pointer hover:text-foreground transition">
          <button className="cursor-pointer " type="button">
            <p>Appearance </p>
          </button>
        </div>
        <div className="py-2 items-center justify-center hover:text-foreground transition cursor-pointer ">
          <button className="cursor-pointer " type="button">
            <p>Settings</p>
          </button>
        </div>
        <div className="py-2 items-center justify-center cursor-pointer hover:text-foreground transition">
          <button className="cursor-pointer " type="button" onClick={handleLogout}>
            <p>Log out</p>
          </button>
        </div>
      </div>
    </div>
  );
}
