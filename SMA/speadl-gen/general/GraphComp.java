package general;

import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.IInit;
import generalStructure.interfaces.UpdateGraph;

@SuppressWarnings("all")
public abstract class GraphComp {
  public interface Requires {
  }
  
  public interface Component extends GraphComp.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IGraph graph();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public UpdateGraph updateGraph();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IInit init();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements GraphComp.Component, GraphComp.Parts {
    private final GraphComp.Requires bridge;
    
    private final GraphComp implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_graph() {
      assert this.graph == null: "This is a bug.";
      this.graph = this.implementation.make_graph();
      if (this.graph == null) {
      	throw new RuntimeException("make_graph() in general.GraphComp should not return null.");
      }
    }
    
    private void init_updateGraph() {
      assert this.updateGraph == null: "This is a bug.";
      this.updateGraph = this.implementation.make_updateGraph();
      if (this.updateGraph == null) {
      	throw new RuntimeException("make_updateGraph() in general.GraphComp should not return null.");
      }
    }
    
    private void init_init() {
      assert this.init == null: "This is a bug.";
      this.init = this.implementation.make_init();
      if (this.init == null) {
      	throw new RuntimeException("make_init() in general.GraphComp should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_graph();
      init_updateGraph();
      init_init();
    }
    
    public ComponentImpl(final GraphComp implem, final GraphComp.Requires b, final boolean doInits) {
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
    
    private IGraph graph;
    
    public IGraph graph() {
      return this.graph;
    }
    
    private UpdateGraph updateGraph;
    
    public UpdateGraph updateGraph() {
      return this.updateGraph;
    }
    
    private IInit init;
    
    public IInit init() {
      return this.init;
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
  
  private GraphComp.ComponentImpl selfComponent;
  
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
  protected GraphComp.Provides provides() {
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
  protected abstract IGraph make_graph();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract UpdateGraph make_updateGraph();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IInit make_init();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected GraphComp.Requires requires() {
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
  protected GraphComp.Parts parts() {
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
  public synchronized GraphComp.Component _newComponent(final GraphComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of GraphComp has already been used to create a component, use another one.");
    }
    this.init = true;
    GraphComp.ComponentImpl  _comp = new GraphComp.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public GraphComp.Component newComponent() {
    return this._newComponent(new GraphComp.Requires() {}, true);
  }
}
