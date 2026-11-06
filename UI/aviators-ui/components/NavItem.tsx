export const NavItem = ({
  icon,
  label,
  onClick,
  isCollapsed,
  selectedCategoryId,
}: {
  readonly icon: string;
  readonly label: string;
  readonly onClick: () => void;
  readonly isCollapsed: boolean;
  readonly selectedCategoryId: string | null;
}) => (
  <button
    onClick={onClick}
    className="w-full flex items-center text-left p-2.5 text-sm text-slate-500 hover:text-slate-700  rounded-lg transition-colors"
  >
    <span className="text-lg mr-3">{icon}</span>
    {!isCollapsed && <span className="font-medium truncate">{label}</span>}
  </button>
);
