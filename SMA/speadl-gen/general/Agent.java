package general;

import agents.interfaces.Do;
import agents.interfaces.ISuicide;
import general.Act;
import general.Decide;
import general.Memory;
import general.Perceive;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.ILog;

@SuppressWarnings("all")
public abstract class Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> {
  public interface Requires<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Context getContext();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ContextUpdate setContext();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CycleAlert finishedCycle();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push push();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull pull();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateAgent create();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ILog finishedCycleForLog();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGraph graph();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ISuicide suicide();
  }
  
  public interface Component<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> extends Agent.Provides<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> {
  }
  
  public interface Provides<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do cycle();
  }
  
  public interface Parts<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Memory.Component<SharedMemory> memory();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Perceive.Component<Context, SharedMemory, Pull> perceive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Decide.Component<Actionable, SharedMemory> decide();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Act.Component<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> act();
  }
  
  public static class ComponentImpl<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> implements Agent.Component<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull>, Agent.Parts<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> {
    private final Agent.Requires<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> bridge;
    
    private final Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> implementation;
    
    public void start() {
      assert this.memory != null: "This is a bug.";
      ((Memory.ComponentImpl<SharedMemory>) this.memory).start();
      assert this.perceive != null: "This is a bug.";
      ((Perceive.ComponentImpl<Context, SharedMemory, Pull>) this.perceive).start();
      assert this.decide != null: "This is a bug.";
      ((Decide.ComponentImpl<Actionable, SharedMemory>) this.decide).start();
      assert this.act != null: "This is a bug.";
      ((Act.ComponentImpl<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push>) this.act).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_memory() {
      assert this.memory == null: "This is a bug.";
      assert this.implem_memory == null: "This is a bug.";
      this.implem_memory = this.implementation.make_memory();
      if (this.implem_memory == null) {
      	throw new RuntimeException("make_memory() in general.Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> should not return null.");
      }
      this.memory = this.implem_memory._newComponent(new BridgeImpl_memory(), false);
      
    }
    
    private void init_perceive() {
      assert this.perceive == null: "This is a bug.";
      assert this.implem_perceive == null: "This is a bug.";
      this.implem_perceive = this.implementation.make_perceive();
      if (this.implem_perceive == null) {
      	throw new RuntimeException("make_perceive() in general.Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> should not return null.");
      }
      this.perceive = this.implem_perceive._newComponent(new BridgeImpl_perceive(), false);
      
    }
    
    private void init_decide() {
      assert this.decide == null: "This is a bug.";
      assert this.implem_decide == null: "This is a bug.";
      this.implem_decide = this.implementation.make_decide();
      if (this.implem_decide == null) {
      	throw new RuntimeException("make_decide() in general.Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> should not return null.");
      }
      this.decide = this.implem_decide._newComponent(new BridgeImpl_decide(), false);
      
    }
    
    private void init_act() {
      assert this.act == null: "This is a bug.";
      assert this.implem_act == null: "This is a bug.";
      this.implem_act = this.implementation.make_act();
      if (this.implem_act == null) {
      	throw new RuntimeException("make_act() in general.Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> should not return null.");
      }
      this.act = this.implem_act._newComponent(new BridgeImpl_act(), false);
      
    }
    
    protected void initParts() {
      init_memory();
      init_perceive();
      init_decide();
      init_act();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Agent<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> implem, final Agent.Requires<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    public Do cycle() {
      return this.perceive().perception();
    }
    
    private Memory.Component<SharedMemory> memory;
    
    private Memory<SharedMemory> implem_memory;
    
    private final class BridgeImpl_memory implements Memory.Requires<SharedMemory> {
    }
    
    public final Memory.Component<SharedMemory> memory() {
      return this.memory;
    }
    
    private Perceive.Component<Context, SharedMemory, Pull> perceive;
    
    private Perceive<Context, SharedMemory, Pull> implem_perceive;
    
    private final class BridgeImpl_perceive implements Perceive.Requires<Context, SharedMemory, Pull> {
      public final Do decision() {
        return Agent.ComponentImpl.this.decide().decision();
      }
      
      public final Context getContext() {
        return Agent.ComponentImpl.this.bridge.getContext();
      }
      
      public final SharedMemory memory() {
        return Agent.ComponentImpl.this.memory().infos();
      }
      
      public final Pull getMessage() {
        return Agent.ComponentImpl.this.bridge.pull();
      }
    }
    
    public final Perceive.Component<Context, SharedMemory, Pull> perceive() {
      return this.perceive;
    }
    
    private Decide.Component<Actionable, SharedMemory> decide;
    
    private Decide<Actionable, SharedMemory> implem_decide;
    
    private final class BridgeImpl_decide implements Decide.Requires<Actionable, SharedMemory> {
      public final Actionable action() {
        return Agent.ComponentImpl.this.act().action();
      }
      
      public final SharedMemory memory() {
        return Agent.ComponentImpl.this.memory().infos();
      }
    }
    
    public final Decide.Component<Actionable, SharedMemory> decide() {
      return this.decide;
    }
    
    private Act.Component<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> act;
    
    private Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> implem_act;
    
    private final class BridgeImpl_act implements Act.Requires<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
      public final CycleAlert finishedCycle() {
        return Agent.ComponentImpl.this.bridge.finishedCycle();
      }
      
      public final ContextUpdate setContext() {
        return Agent.ComponentImpl.this.bridge.setContext();
      }
      
      public final SharedMemory memory() {
        return Agent.ComponentImpl.this.memory().infos();
      }
      
      public final CreateAgent create() {
        return Agent.ComponentImpl.this.bridge.create();
      }
      
      public final ILog finishedCycleForLog() {
        return Agent.ComponentImpl.this.bridge.finishedCycleForLog();
      }
      
      public final IGraph graph() {
        return Agent.ComponentImpl.this.bridge.graph();
      }
      
      public final Push sendMessage() {
        return Agent.ComponentImpl.this.bridge.push();
      }
      
      public final ISuicide suicide() {
        return Agent.ComponentImpl.this.bridge.suicide();
      }
    }
    
    public final Act.Component<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> act() {
      return this.act;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private Agent.ComponentImpl<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected Agent.Provides<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Agent.Requires<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected Agent.Parts<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Memory<SharedMemory> make_memory();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Perceive<Context, SharedMemory, Pull> make_perceive();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Decide<Actionable, SharedMemory> make_decide();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> make_act();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Agent.Component<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> _newComponent(final Agent.Requires<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Agent has already been used to create a component, use another one.");
    }
    this.init = true;
    Agent.ComponentImpl<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull>  _comp = new Agent.ComponentImpl<Context, ContextUpdate, Actionable, SharedMemory, CreateAgent, Push, Pull>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
