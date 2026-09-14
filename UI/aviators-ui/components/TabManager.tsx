"use client";
// 1. Import the global context instead of useState
import { useWorkspace } from '@/context/WorkspaceContext';

export default function TabManager() {
  // 2. Pull the global state and functions directly from the context
  const { tabs, activeTabId, closeTab, setActiveTabId } = useWorkspace() as any; 

  // 3. Fallback UI if all tabs are closed
  if (tabs.length === 0) {
    return (
      <div className="flex-1 flex flex-col items-center justify-center text-gray-500 h-full">
        <p>No tabs open. Select an item from the menu.</p>
      </div>
    );
  }

  return (
    <div className="h-full flex flex-col">
      {/* Tab Header Row */}
      <div className="flex bg-gray-900 overflow-x-auto border-b border-gray-700 p-1">
        {tabs.map((tab: any) => (
          <div 
            key={tab.id}
            // 4. Use the global setActiveTabId function
            onClick={() => setActiveTabId(tab.id)} 
            className={`flex items-center px-4 py-2 mx-1 cursor-pointer rounded-t-md text-sm border-t border-x border-transparent transition-colors
              ${activeTabId === tab.id ? 'bg-gray-800 border-gray-700 text-white' : 'text-gray-400 hover:bg-gray-800'}`}
          >
            <span className="mr-3">{tab.title}</span>
            {/* 5. Use the global closeTab function */}
            <button onClick={(e) => closeTab(tab.id, e)} className="hover:text-red-400">✕</button>
          </div>
        ))}
      </div>

      {/* Main Form/Read Area */}
      <div className="flex-1 p-6 overflow-y-auto">
        {tabs.map((tab: any) => (
          <div key={tab.id} className={activeTabId === tab.id ? 'block' : 'hidden'}>
             <h2 className="text-2xl font-bold mb-4">{tab.title} workspace</h2>
             
             {tab.type === 'CREATE' && (
               <form className="space-y-4">
                 <input type="text" placeholder="Title" className="w-full p-2 bg-gray-900 border border-gray-700 rounded" />
                 <textarea placeholder="Summary" className="w-full p-2 bg-gray-900 border border-gray-700 rounded h-24" />
                 <textarea placeholder="Content" className="w-full p-2 bg-gray-900 border border-gray-700 rounded h-64" />
               </form>
             )}
          </div>
        ))}
      </div>
    </div>
  );
}