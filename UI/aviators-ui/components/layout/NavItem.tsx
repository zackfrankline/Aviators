import { ReactNode } from "react";

export const NavItem = ({
  icon,
  label,
  onClick,
  isCollapsed,
  selectedCategoryId,
}: {
  readonly icon: ReactNode;
  readonly label: string;
  readonly onClick: () => void;
  readonly isCollapsed: boolean;
  readonly selectedCategoryId: string | null;
}) => (
  <button
    onClick={onClick}
    className="w-full flex items-center text-left py-2.5 px-1.5  p-2.5 text-sm text-secondary-foreground hover:text-foreground rounded-lg transition-colors"
  >
    <div>{icon}</div>
    {!isCollapsed && <span className="font-medium truncate ml-2.5 text-md">{label}</span>}
  </button>
);
