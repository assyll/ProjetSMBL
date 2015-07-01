package general;

import environnement.interfaces.CellInfo;
import generalStructure.interfaces.IInit;
import java.util.List;
import trace.Action;

@SuppressWarnings("all")
public abstract class Environnement<Context, ContextUpdate> {
  public interface Requires<Context, ContextUpdate> {
  }
  
  public interface Component<Context, ContextUpdate> extends Environnement.Provides<Context, ContextUpdate> {
  }
  
  public interface Provides<Context, ContextUpdate> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Context envInfos();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public ContextUpdate envUpdate();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IInit init();
  }
  
  public interface Parts<Context, ContextUpdate> {
  }
  
  public static class ComponentImpl<Context, ContextUpdate> implements Environnement.Component<Context, ContextUpdate>, Environnement.Parts<Context, ContextUpdate> {
    private final Environnement.Requires<Context, ContextUpdate> bridge;
    
    private final Environnement<Context, ContextUpdate> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_envInfos() {
      assert this.envInfos == null: "This is a bug.";
      this.envInfos = this.implementation.make_envInfos();
      if (this.envInfos == null) {
      	throw new RuntimeException("make_envInfos() in general.Environnement<Context, ContextUpdate> should not return null.");
      }
    }
    
    private void init_envUpdate() {
      assert this.envUpdate == null: "This is a bug.";
      this.envUpdate = this.implementation.make_envUpdate();
      if (this.envUpdate == null) {
      	throw new RuntimeException("make_envUpdate() in general.Environnement<Context, ContextUpdate> should not return null.");
      }
    }
    
    private void init_init() {
      assert this.init == null: "This is a bug.";
      this.init = this.implementation.make_init();
      if (this.init == null) {
      	throw new RuntimeException("make_init() in general.Environnement<Context, ContextUpdate> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_envInfos();
      init_envUpdate();
      init_init();
    }
    
    public ComponentImpl(final Environnement<Context, ContextUpdate> implem, final Environnement.Requires<Context, ContextUpdate> b, final boolean doInits) {
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
    
    private Context envInfos;
    
    public Context envInfos() {
      return this.envInfos;
    }
    
    private ContextUpdate envUpdate;
    
    public ContextUpdate envUpdate() {
      return this.envUpdate;
    }
    
    private IInit init;
    
    public IInit init() {
      return this.init;
    }
  }
  
  public static abstract class Cell<Context, ContextUpdate> {
    public interface Requires<Context, ContextUpdate> {
    }
    
    public interface Component<Context, ContextUpdate> extends Environnement.Cell.Provides<Context, ContextUpdate> {
    }
    
    public interface Provides<Context, ContextUpdate> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public CellInfo cellInfos();
    }
    
    public interface Parts<Context, ContextUpdate> {
    }
    
    public static class ComponentImpl<Context, ContextUpdate> implements Environnement.Cell.Component<Context, ContextUpdate>, Environnement.Cell.Parts<Context, ContextUpdate> {
      private final Environnement.Cell.Requires<Context, ContextUpdate> bridge;
      
      private final Environnement.Cell<Context, ContextUpdate> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_cellInfos() {
        assert this.cellInfos == null: "This is a bug.";
        this.cellInfos = this.implementation.make_cellInfos();
        if (this.cellInfos == null) {
        	throw new RuntimeException("make_cellInfos() in general.Environnement$Cell<Context, ContextUpdate> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_cellInfos();
      }
      
      public ComponentImpl(final Environnement.Cell<Context, ContextUpdate> implem, final Environnement.Cell.Requires<Context, ContextUpdate> b, final boolean doInits) {
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
      
      private CellInfo cellInfos;
      
      public CellInfo cellInfos() {
        return this.cellInfos;
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
    
    private Environnement.Cell.ComponentImpl<Context, ContextUpdate> selfComponent;
    
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
    protected Environnement.Cell.Provides<Context, ContextUpdate> provides() {
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
    protected abstract CellInfo make_cellInfos();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Environnement.Cell.Requires<Context, ContextUpdate> requires() {
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
    protected Environnement.Cell.Parts<Context, ContextUpdate> parts() {
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
    public synchronized Environnement.Cell.Component<Context, ContextUpdate> _newComponent(final Environnement.Cell.Requires<Context, ContextUpdate> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Cell has already been used to create a component, use another one.");
      }
      this.init = true;
      Environnement.Cell.ComponentImpl<Context, ContextUpdate>  _comp = new Environnement.Cell.ComponentImpl<Context, ContextUpdate>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Environnement.ComponentImpl<Context, ContextUpdate> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Environnement.Provides<Context, ContextUpdate> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Environnement.Requires<Context, ContextUpdate> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Environnement.Parts<Context, ContextUpdate> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
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
  
  private Environnement.ComponentImpl<Context, ContextUpdate> selfComponent;
  
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
  protected Environnement.Provides<Context, ContextUpdate> provides() {
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
  protected abstract Context make_envInfos();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ContextUpdate make_envUpdate();
  
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
  protected Environnement.Requires<Context, ContextUpdate> requires() {
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
  protected Environnement.Parts<Context, ContextUpdate> parts() {
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
  public synchronized Environnement.Component<Context, ContextUpdate> _newComponent(final Environnement.Requires<Context, ContextUpdate> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Environnement has already been used to create a component, use another one.");
    }
    this.init = true;
    Environnement.ComponentImpl<Context, ContextUpdate>  _comp = new Environnement.ComponentImpl<Context, ContextUpdate>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Environnement.Cell<Context, ContextUpdate> make_Cell(final List<Action> actions);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Environnement.Cell<Context, ContextUpdate> _createImplementationOfCell(final List<Action> actions) {
    Environnement.Cell<Context, ContextUpdate> implem = make_Cell(actions);
    if (implem == null) {
    	throw new RuntimeException("make_Cell() in general.Environnement should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected Environnement.Cell.Component<Context, ContextUpdate> newCell(final List<Action> actions) {
    Environnement.Cell<Context, ContextUpdate> _implem = _createImplementationOfCell(actions);
    return _implem._newComponent(new Environnement.Cell.Requires<Context, ContextUpdate>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Environnement.Component<Context, ContextUpdate> newComponent() {
    return this._newComponent(new Environnement.Requires<Context, ContextUpdate>() {}, true);
  }
}
