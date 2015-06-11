package general;

import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.ILog;
import generalStructure.interfaces.IStop;

@SuppressWarnings("all")
public abstract class Forward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
  public interface Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CycleAlert i();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ActionGetter j();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ContextTA h();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ContextUpdate k();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ILog log();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGraph graph();
  }
  
  public interface Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> extends Forward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
  }
  
  public interface Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push l();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IStop stopProcessus();
  }
  
  public interface Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
  }
  
  public static class ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implements Forward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>, Forward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    private final Forward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> bridge;
    
    private final Forward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_l() {
      assert this.l == null: "This is a bug.";
      this.l = this.implementation.make_l();
      if (this.l == null) {
      	throw new RuntimeException("make_l() in general.Forward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
      }
    }
    
    private void init_stopProcessus() {
      assert this.stopProcessus == null: "This is a bug.";
      this.stopProcessus = this.implementation.make_stopProcessus();
      if (this.stopProcessus == null) {
      	throw new RuntimeException("make_stopProcessus() in general.Forward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_l();
      init_stopProcessus();
    }
    
    public ComponentImpl(final Forward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implem, final Forward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> b, final boolean doInits) {
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
    
    private Push l;
    
    public Push l() {
      return this.l;
    }
    
    private IStop stopProcessus;
    
    public IStop stopProcessus() {
      return this.stopProcessus;
    }
  }
  
  public static abstract class StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    public interface Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    }
    
    public interface Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> extends Forward.StateForward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    }
    
    public interface Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public CycleAlert a();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ContextSA b();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ContextUpdate c();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push d();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull e();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ILog finishedCycleForLog();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IGraph graph();
    }
    
    public interface Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    }
    
    public static class ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implements Forward.StateForward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>, Forward.StateForward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
      private final Forward.StateForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> bridge;
      
      private final Forward.StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_a() {
        assert this.a == null: "This is a bug.";
        this.a = this.implementation.make_a();
        if (this.a == null) {
        	throw new RuntimeException("make_a() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_b() {
        assert this.b == null: "This is a bug.";
        this.b = this.implementation.make_b();
        if (this.b == null) {
        	throw new RuntimeException("make_b() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_c() {
        assert this.c == null: "This is a bug.";
        this.c = this.implementation.make_c();
        if (this.c == null) {
        	throw new RuntimeException("make_c() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_d() {
        assert this.d == null: "This is a bug.";
        this.d = this.implementation.make_d();
        if (this.d == null) {
        	throw new RuntimeException("make_d() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_e() {
        assert this.e == null: "This is a bug.";
        this.e = this.implementation.make_e();
        if (this.e == null) {
        	throw new RuntimeException("make_e() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_finishedCycleForLog() {
        assert this.finishedCycleForLog == null: "This is a bug.";
        this.finishedCycleForLog = this.implementation.make_finishedCycleForLog();
        if (this.finishedCycleForLog == null) {
        	throw new RuntimeException("make_finishedCycleForLog() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_graph() {
        assert this.graph == null: "This is a bug.";
        this.graph = this.implementation.make_graph();
        if (this.graph == null) {
        	throw new RuntimeException("make_graph() in general.Forward$StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_a();
        init_b();
        init_c();
        init_d();
        init_e();
        init_finishedCycleForLog();
        init_graph();
      }
      
      public ComponentImpl(final Forward.StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implem, final Forward.StateForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> b, final boolean doInits) {
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
      
      private CycleAlert a;
      
      public CycleAlert a() {
        return this.a;
      }
      
      private ContextSA b;
      
      public ContextSA b() {
        return this.b;
      }
      
      private ContextUpdate c;
      
      public ContextUpdate c() {
        return this.c;
      }
      
      private Push d;
      
      public Push d() {
        return this.d;
      }
      
      private Pull e;
      
      public Pull e() {
        return this.e;
      }
      
      private ILog finishedCycleForLog;
      
      public ILog finishedCycleForLog() {
        return this.finishedCycleForLog;
      }
      
      private IGraph graph;
      
      public IGraph graph() {
        return this.graph;
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
    
    private Forward.StateForward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> selfComponent;
    
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
    protected Forward.StateForward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> provides() {
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
    protected abstract CycleAlert make_a();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ContextSA make_b();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ContextUpdate make_c();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Push make_d();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull make_e();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ILog make_finishedCycleForLog();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IGraph make_graph();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Forward.StateForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> requires() {
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
    protected Forward.StateForward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> parts() {
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
    public synchronized Forward.StateForward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _newComponent(final Forward.StateForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of StateForward has already been used to create a component, use another one.");
      }
      this.init = true;
      Forward.StateForward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>  _comp = new Forward.StateForward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Forward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Forward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Forward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Forward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static abstract class TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    public interface Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    }
    
    public interface Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> extends Forward.TransForward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    }
    
    public interface Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public CycleAlert a();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ContextTA b();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ContextUpdate c();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push d();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull e();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ILog finishedCycleForLog();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IGraph graph();
    }
    
    public interface Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
    }
    
    public static class ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implements Forward.TransForward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>, Forward.TransForward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> {
      private final Forward.TransForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> bridge;
      
      private final Forward.TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_a() {
        assert this.a == null: "This is a bug.";
        this.a = this.implementation.make_a();
        if (this.a == null) {
        	throw new RuntimeException("make_a() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_b() {
        assert this.b == null: "This is a bug.";
        this.b = this.implementation.make_b();
        if (this.b == null) {
        	throw new RuntimeException("make_b() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_c() {
        assert this.c == null: "This is a bug.";
        this.c = this.implementation.make_c();
        if (this.c == null) {
        	throw new RuntimeException("make_c() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_d() {
        assert this.d == null: "This is a bug.";
        this.d = this.implementation.make_d();
        if (this.d == null) {
        	throw new RuntimeException("make_d() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_e() {
        assert this.e == null: "This is a bug.";
        this.e = this.implementation.make_e();
        if (this.e == null) {
        	throw new RuntimeException("make_e() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_finishedCycleForLog() {
        assert this.finishedCycleForLog == null: "This is a bug.";
        this.finishedCycleForLog = this.implementation.make_finishedCycleForLog();
        if (this.finishedCycleForLog == null) {
        	throw new RuntimeException("make_finishedCycleForLog() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      private void init_graph() {
        assert this.graph == null: "This is a bug.";
        this.graph = this.implementation.make_graph();
        if (this.graph == null) {
        	throw new RuntimeException("make_graph() in general.Forward$TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_a();
        init_b();
        init_c();
        init_d();
        init_e();
        init_finishedCycleForLog();
        init_graph();
      }
      
      public ComponentImpl(final Forward.TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implem, final Forward.TransForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> b, final boolean doInits) {
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
      
      private CycleAlert a;
      
      public CycleAlert a() {
        return this.a;
      }
      
      private ContextTA b;
      
      public ContextTA b() {
        return this.b;
      }
      
      private ContextUpdate c;
      
      public ContextUpdate c() {
        return this.c;
      }
      
      private Push d;
      
      public Push d() {
        return this.d;
      }
      
      private Pull e;
      
      public Pull e() {
        return this.e;
      }
      
      private ILog finishedCycleForLog;
      
      public ILog finishedCycleForLog() {
        return this.finishedCycleForLog;
      }
      
      private IGraph graph;
      
      public IGraph graph() {
        return this.graph;
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
    
    private Forward.TransForward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> selfComponent;
    
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
    protected Forward.TransForward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> provides() {
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
    protected abstract CycleAlert make_a();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ContextTA make_b();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ContextUpdate make_c();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Push make_d();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull make_e();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ILog make_finishedCycleForLog();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IGraph make_graph();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Forward.TransForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> requires() {
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
    protected Forward.TransForward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> parts() {
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
    public synchronized Forward.TransForward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _newComponent(final Forward.TransForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of TransForward has already been used to create a component, use another one.");
      }
      this.init = true;
      Forward.TransForward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>  _comp = new Forward.TransForward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Forward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Forward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Forward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Forward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> eco_parts() {
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
  
  private Forward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> selfComponent;
  
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
  protected Forward.Provides<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> provides() {
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
  protected abstract Push make_l();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IStop make_stopProcessus();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Forward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> requires() {
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
  protected Forward.Parts<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> parts() {
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
  public synchronized Forward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _newComponent(final Forward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Forward has already been used to create a component, use another one.");
    }
    this.init = true;
    Forward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>  _comp = new Forward.ComponentImpl<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Forward.StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> make_StateForward(final String id, final boolean isRoot);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Forward.StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _createImplementationOfStateForward(final String id, final boolean isRoot) {
    Forward.StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implem = make_StateForward(id,isRoot);
    if (implem == null) {
    	throw new RuntimeException("make_StateForward() in general.Forward should not return null.");
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
  protected Forward.StateForward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> newStateForward(final String id, final boolean isRoot) {
    Forward.StateForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _implem = _createImplementationOfStateForward(id,isRoot);
    return _implem._newComponent(new Forward.StateForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Forward.TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> make_TransForward(final String id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Forward.TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _createImplementationOfTransForward(final String id) {
    Forward.TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> implem = make_TransForward(id);
    if (implem == null) {
    	throw new RuntimeException("make_TransForward() in general.Forward should not return null.");
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
  protected Forward.TransForward.Component<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> newTransForward(final String id) {
    Forward.TransForward<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter> _implem = _createImplementationOfTransForward(id);
    return _implem._newComponent(new Forward.TransForward.Requires<CycleAlert, ContextSA, ContextTA, ContextUpdate, Push, Pull, ActionGetter>() {},true);
  }
}
