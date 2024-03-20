import clsx from "clsx";
import {
  createContext,
  Dispatch,
  ReactNode,
  useContext,
  useReducer,
} from "react";
import Folder from "../../atoms/Icons/Folder";
import { useNodeNames } from "./customHooks/NodeNames";

type TreeViewState = Map<string, boolean>;

enum TreeViewActionTypes {
  OPEN = "OPEN",
  CLOSE = "CLOSE",
}

type TreeViewActions =
  | {
      type: TreeViewActionTypes.OPEN;
      id: string;
    }
  | {
      type: TreeViewActionTypes.CLOSE;
      id: string;
    };

function treeviewHandler(
  state: TreeViewState,
  action: TreeViewActions
): TreeViewState {
  switch (action.type) {
    case TreeViewActionTypes.OPEN:
      return new Map(state).set(action.id, true);

    case TreeViewActionTypes.CLOSE:
      return new Map(state).set(action.id, false);

    default:
      throw new Error("Can't handle this action.");
  }
}

type TreeViewContextType = {
  open: TreeViewState;
  dispatch: Dispatch<TreeViewActions>;
  selectedId: string | null;
  selectId: (id: string) => void;
};

const TreeViewContext = createContext<TreeViewContextType>({
  open: new Map<string, boolean>(),
  dispatch: () => {},
  selectedId: null,
  selectId: () => {},
});

type RootProps = {
  children: ReactNode | ReactNode[];
  className?: string;
  value: string | null;
  onChange: (id: string) => void;
};

function Root({ children, className, value, onChange }: RootProps) {
  const [open, dispatch] = useReducer(
    treeviewHandler,
    new Map<string, boolean>()
  );

  return (
    <TreeViewContext.Provider
      value={{
        open,
        dispatch,
        selectedId: value,
        selectId: onChange,
      }}
    >
      <ul className="flex flex-col overflow-auto">{children}</ul>
    </TreeViewContext.Provider>
  );
}

type TreeNodeType = {
  id: string;
  name: string;
  children?: TreeNodeType[];
  icon?: ReactNode;
};

type NodeProps = {
  node: TreeNodeType;
  initialState: boolean;
  handleCheckBoxState: (
    e: React.ChangeEvent<HTMLInputElement>,
    id: string
  ) => void;
};

const Node: React.FC<NodeProps> = ({
  node: { id, name, children },
  initialState,
  handleCheckBoxState,
}) => {
  const { open, dispatch, selectId, selectedId } = useContext(TreeViewContext);

  const { nodeName, setNodes } = useNodeNames();

  const isOpen = open.get(id);

  const isFile = !children || children.length === 0;

  const handleCheckBox = (
    e: React.ChangeEvent<HTMLInputElement>,
    id: string
  ) => {
    if (isFile) {
      handleCheckBoxState(e, id);
      dispatch({
        id: id,
        type: isOpen ? TreeViewActionTypes.CLOSE : TreeViewActionTypes.OPEN,
      });
      if (!initialState && !nodeName.includes(name)) {
        const updateNodeNames = [...nodeName, name];
        setNodes(updateNodeNames);
      } else if (initialState && nodeName.includes(name)) {
        const updateNodeNames = nodeName.filter((n) => n !== name);
        setNodes(updateNodeNames);
      }
    }
  };

  return (
    <li className="flex flex-col cursor-pointer select-none pl-3 pb-2 md:pl-4 lg:pl-5 xl:pl-6 2xl:pl-7">
      <div
        className={clsx(
          "flex items-center space-x-2 px-8",
          selectedId === id ? "bg-slate-200" : "bg-transparent"
        )}
      >
        {isFile ? (
          <input
            type="checkbox"
            checked={!!initialState}
            onChange={(e: any) => {
              handleCheckBox(e, id);
            }}
            id={id}
            className="h-5 w-5 md:mb-0.5 lg:mb-0.5 xl:mb-1 2xl:mb-1.5"
          />
        ) : (
          <Folder className="h-4 w-4" open={isOpen} />
        )}
        <span className="text-[#343433] font-normal text-3xl mb-2 md:text-xl lg:text-2xl xl:text-2xl 2xl:text-3xl">
          {name}
        </span>
      </div>
    </li>
  );
};

export const TreeView = { Root, Node };
export type { TreeNodeType };
