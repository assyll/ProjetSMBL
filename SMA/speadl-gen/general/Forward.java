package general;

@SuppressWarnings("all")
public abstract class Forward<I, J, K, L, M> {
  public interface Requires<I, J, K, L, M> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public I i();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public J j();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public K k();
  }
  
  public interface Component<I, J, K, L, M> extends Forward.Provides<I, J, K, L, M> {
  }
  
  public interface Provides<I, J, K, L, M> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public L l();
  }
  
  public interface Parts<I, J, K, L, M> {
  }
  
  public static class ComponentImpl<I, J, K, L, M> implements Forward.Component<I, J, K, L, M>, Forward.Parts<I, J, K, L, M> {
    private final Forward.Requires<I, J, K, L, M> bridge;
    
    private final Forward<I, J, K, L, M> implementation;
    
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
      	throw new RuntimeException("make_l() in general.Forward<I, J, K, L, M> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_l();
    }
    
    public ComponentImpl(final Forward<I, J, K, L, M> implem, final Forward.Requires<I, J, K, L, M> b, final boolean doInits) {
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
    
    private L l;
    
    public L l() {
      return this.l;
    }
  }
  
  public static abstract class StateForward<I, J, K, L, M> {
    public interface Requires<I, J, K, L, M> {
    }
    
    public interface Component<I, J, K, L, M> extends Forward.StateForward.Provides<I, J, K, L, M> {
    }
    
    public interface Provides<I, J, K, L, M> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public I a();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public J b();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public K c();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public L d();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public M e();
    }
    
    public interface Parts<I, J, K, L, M> {
    }
    
    public static class ComponentImpl<I, J, K, L, M> implements Forward.StateForward.Component<I, J, K, L, M>, Forward.StateForward.Parts<I, J, K, L, M> {
      private final Forward.StateForward.Requires<I, J, K, L, M> bridge;
      
      private final Forward.StateForward<I, J, K, L, M> implementation;
      
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
        	throw new RuntimeException("make_a() in general.Forward$StateForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_b() {
        assert this.b == null: "This is a bug.";
        this.b = this.implementation.make_b();
        if (this.b == null) {
        	throw new RuntimeException("make_b() in general.Forward$StateForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_c() {
        assert this.c == null: "This is a bug.";
        this.c = this.implementation.make_c();
        if (this.c == null) {
        	throw new RuntimeException("make_c() in general.Forward$StateForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_d() {
        assert this.d == null: "This is a bug.";
        this.d = this.implementation.make_d();
        if (this.d == null) {
        	throw new RuntimeException("make_d() in general.Forward$StateForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_e() {
        assert this.e == null: "This is a bug.";
        this.e = this.implementation.make_e();
        if (this.e == null) {
        	throw new RuntimeException("make_e() in general.Forward$StateForward<I, J, K, L, M> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_a();
        init_b();
        init_c();
        init_d();
        init_e();
      }
      
      public ComponentImpl(final Forward.StateForward<I, J, K, L, M> implem, final Forward.StateForward.Requires<I, J, K, L, M> b, final boolean doInits) {
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
      
      private I a;
      
      public I a() {
        return this.a;
      }
      
      private J b;
      
      public J b() {
        return this.b;
      }
      
      private K c;
      
      public K c() {
        return this.c;
      }
      
      private L d;
      
      public L d() {
        return this.d;
      }
      
      private M e;
      
      public M e() {
        return this.e;
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
    
    private Forward.StateForward.ComponentImpl<I, J, K, L, M> selfComponent;
    
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
    protected Forward.StateForward.Provides<I, J, K, L, M> provides() {
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
    protected abstract I make_a();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract J make_b();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract K make_c();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract L make_d();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract M make_e();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Forward.StateForward.Requires<I, J, K, L, M> requires() {
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
    protected Forward.StateForward.Parts<I, J, K, L, M> parts() {
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
    public synchronized Forward.StateForward.Component<I, J, K, L, M> _newComponent(final Forward.StateForward.Requires<I, J, K, L, M> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of StateForward has already been used to create a component, use another one.");
      }
      this.init = true;
      Forward.StateForward.ComponentImpl<I, J, K, L, M>  _comp = new Forward.StateForward.ComponentImpl<I, J, K, L, M>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Forward.ComponentImpl<I, J, K, L, M> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Forward.Provides<I, J, K, L, M> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Forward.Requires<I, J, K, L, M> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Forward.Parts<I, J, K, L, M> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static abstract class TransForward<I, J, K, L, M> {
    public interface Requires<I, J, K, L, M> {
    }
    
    public interface Component<I, J, K, L, M> extends Forward.TransForward.Provides<I, J, K, L, M> {
    }
    
    public interface Provides<I, J, K, L, M> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public I a();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public J b();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public K c();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public L d();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public M e();
    }
    
    public interface Parts<I, J, K, L, M> {
    }
    
    public static class ComponentImpl<I, J, K, L, M> implements Forward.TransForward.Component<I, J, K, L, M>, Forward.TransForward.Parts<I, J, K, L, M> {
      private final Forward.TransForward.Requires<I, J, K, L, M> bridge;
      
      private final Forward.TransForward<I, J, K, L, M> implementation;
      
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
        	throw new RuntimeException("make_a() in general.Forward$TransForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_b() {
        assert this.b == null: "This is a bug.";
        this.b = this.implementation.make_b();
        if (this.b == null) {
        	throw new RuntimeException("make_b() in general.Forward$TransForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_c() {
        assert this.c == null: "This is a bug.";
        this.c = this.implementation.make_c();
        if (this.c == null) {
        	throw new RuntimeException("make_c() in general.Forward$TransForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_d() {
        assert this.d == null: "This is a bug.";
        this.d = this.implementation.make_d();
        if (this.d == null) {
        	throw new RuntimeException("make_d() in general.Forward$TransForward<I, J, K, L, M> should not return null.");
        }
      }
      
      private void init_e() {
        assert this.e == null: "This is a bug.";
        this.e = this.implementation.make_e();
        if (this.e == null) {
        	throw new RuntimeException("make_e() in general.Forward$TransForward<I, J, K, L, M> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_a();
        init_b();
        init_c();
        init_d();
        init_e();
      }
      
      public ComponentImpl(final Forward.TransForward<I, J, K, L, M> implem, final Forward.TransForward.Requires<I, J, K, L, M> b, final boolean doInits) {
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
      
      private I a;
      
      public I a() {
        return this.a;
      }
      
      private J b;
      
      public J b() {
        return this.b;
      }
      
      private K c;
      
      public K c() {
        return this.c;
      }
      
      private L d;
      
      public L d() {
        return this.d;
      }
      
      private M e;
      
      public M e() {
        return this.e;
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
    
    private Forward.TransForward.ComponentImpl<I, J, K, L, M> selfComponent;
    
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
    protected Forward.TransForward.Provides<I, J, K, L, M> provides() {
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
    protected abstract I make_a();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract J make_b();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract K make_c();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract L make_d();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract M make_e();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Forward.TransForward.Requires<I, J, K, L, M> requires() {
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
    protected Forward.TransForward.Parts<I, J, K, L, M> parts() {
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
    public synchronized Forward.TransForward.Component<I, J, K, L, M> _newComponent(final Forward.TransForward.Requires<I, J, K, L, M> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of TransForward has already been used to create a component, use another one.");
      }
      this.init = true;
      Forward.TransForward.ComponentImpl<I, J, K, L, M>  _comp = new Forward.TransForward.ComponentImpl<I, J, K, L, M>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Forward.ComponentImpl<I, J, K, L, M> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Forward.Provides<I, J, K, L, M> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Forward.Requires<I, J, K, L, M> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Forward.Parts<I, J, K, L, M> eco_parts() {
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
  
  private Forward.ComponentImpl<I, J, K, L, M> selfComponent;
  
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
  protected Forward.Provides<I, J, K, L, M> provides() {
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
  protected abstract L make_l();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Forward.Requires<I, J, K, L, M> requires() {
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
  protected Forward.Parts<I, J, K, L, M> parts() {
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
  public synchronized Forward.Component<I, J, K, L, M> _newComponent(final Forward.Requires<I, J, K, L, M> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Forward has already been used to create a component, use another one.");
    }
    this.init = true;
    Forward.ComponentImpl<I, J, K, L, M>  _comp = new Forward.ComponentImpl<I, J, K, L, M>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Forward.StateForward<I, J, K, L, M> make_StateForward(final String id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Forward.StateForward<I, J, K, L, M> _createImplementationOfStateForward(final String id) {
    Forward.StateForward<I, J, K, L, M> implem = make_StateForward(id);
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
  protected Forward.StateForward.Component<I, J, K, L, M> newStateForward(final String id) {
    Forward.StateForward<I, J, K, L, M> _implem = _createImplementationOfStateForward(id);
    return _implem._newComponent(new Forward.StateForward.Requires<I, J, K, L, M>() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Forward.TransForward<I, J, K, L, M> make_TransForward(final String id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Forward.TransForward<I, J, K, L, M> _createImplementationOfTransForward(final String id) {
    Forward.TransForward<I, J, K, L, M> implem = make_TransForward(id);
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
  protected Forward.TransForward.Component<I, J, K, L, M> newTransForward(final String id) {
    Forward.TransForward<I, J, K, L, M> _implem = _createImplementationOfTransForward(id);
    return _implem._newComponent(new Forward.TransForward.Requires<I, J, K, L, M>() {},true);
  }
}
