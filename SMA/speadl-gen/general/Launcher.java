package general;

import agents.IStart;
import agents.interfaces.Callable;
import agents.interfaces.IGetThread;
import environnement.interfaces.IWriteEnv;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.IControl;
import generalStructure.interfaces.IInit;
import generalStructure.interfaces.IStop;

@SuppressWarnings("all")
public abstract class Launcher {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IStart start();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IStop stopProcessus();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IInit initAll();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IWriteEnv writeEnv();
  }
  
  public interface Component extends Launcher.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Callable call();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public CycleAlert finishedCycle();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IGetThread threads();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IStop stopAllAgents();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IControl control();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Launcher.Component, Launcher.Parts {
    private final Launcher.Requires bridge;
    
    private final Launcher implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_call() {
      assert this.call == null: "This is a bug.";
      this.call = this.implementation.make_call();
      if (this.call == null) {
      	throw new RuntimeException("make_call() in general.Launcher should not return null.");
      }
    }
    
    private void init_finishedCycle() {
      assert this.finishedCycle == null: "This is a bug.";
      this.finishedCycle = this.implementation.make_finishedCycle();
      if (this.finishedCycle == null) {
      	throw new RuntimeException("make_finishedCycle() in general.Launcher should not return null.");
      }
    }
    
    private void init_threads() {
      assert this.threads == null: "This is a bug.";
      this.threads = this.implementation.make_threads();
      if (this.threads == null) {
      	throw new RuntimeException("make_threads() in general.Launcher should not return null.");
      }
    }
    
    private void init_stopAllAgents() {
      assert this.stopAllAgents == null: "This is a bug.";
      this.stopAllAgents = this.implementation.make_stopAllAgents();
      if (this.stopAllAgents == null) {
      	throw new RuntimeException("make_stopAllAgents() in general.Launcher should not return null.");
      }
    }
    
    private void init_control() {
      assert this.control == null: "This is a bug.";
      this.control = this.implementation.make_control();
      if (this.control == null) {
      	throw new RuntimeException("make_control() in general.Launcher should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_call();
      init_finishedCycle();
      init_threads();
      init_stopAllAgents();
      init_control();
    }
    
    public ComponentImpl(final Launcher implem, final Launcher.Requires b, final boolean doInits) {
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
    
    private Callable call;
    
    public Callable call() {
      return this.call;
    }
    
    private CycleAlert finishedCycle;
    
    public CycleAlert finishedCycle() {
      return this.finishedCycle;
    }
    
    private IGetThread threads;
    
    public IGetThread threads() {
      return this.threads;
    }
    
    private IStop stopAllAgents;
    
    public IStop stopAllAgents() {
      return this.stopAllAgents;
    }
    
    private IControl control;
    
    public IControl control() {
      return this.control;
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
  
  private Launcher.ComponentImpl selfComponent;
  
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
  protected Launcher.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Callable make_call();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract CycleAlert make_finishedCycle();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IGetThread make_threads();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IStop make_stopAllAgents();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IControl make_control();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Launcher.Requires requires() {
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
  protected Launcher.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Launcher.Component _newComponent(final Launcher.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Launcher has already been used to create a component, use another one.");
    }
    this.init = true;
    Launcher.ComponentImpl  _comp = new Launcher.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
