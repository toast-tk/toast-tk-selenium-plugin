package io.toast.tk.adapter.web;


import io.toast.tk.adapter.web.component.WebAutoElement;
import io.toast.tk.adapter.web.component.WebButtonElement;
import io.toast.tk.adapter.web.component.WebContainerElement;
import io.toast.tk.adapter.web.component.WebInputElement;
import io.toast.tk.adapter.web.component.WebLinkElement;
import io.toast.tk.adapter.web.component.WebSelectElement;
import io.toast.tk.adapter.web.component.WebTableElement;
import io.toast.tk.core.adapter.AutoWebType;
import io.toast.tk.core.runtime.IWebAutoElement;
import io.toast.tk.core.runtime.IWebElementDescriptor;

public class WebElementFactory implements IWebComponentFactory{

	@Override
	public WebAutoElement getElement(
		IWebElementDescriptor e) {
		switch(e.getType()) {
			case button :
				return new WebButtonElement(e);
			case input :
				return new WebInputElement(e);
			case select :
				return new WebSelectElement(e);
			case link :
				return new WebLinkElement(e);
			case table:
				return new WebTableElement(e);
			case component:
				return new WebContainerElement(e);
			default :
				return new WebAutoElement(e);
		}
	}

	public Class<? extends IWebAutoElement<?>> getTypeClass(
		AutoWebType e) {
		switch(e) {
			case button : return WebButtonElement.class;
			case input : return WebInputElement.class;
			case list : return WebSelectElement.class;
			case select : return WebSelectElement.class;
			case link :	return WebLinkElement.class;
			case table : return WebTableElement.class;
			case component: return WebContainerElement.class;
			default :
				return WebAutoElement.class;
		}
	}

	public AutoWebType getClassAutoType(
		Class<?> e) {
		if(e.equals(WebButtonElement.class)) {
			return AutoWebType.button;
		}
		else if(e.equals(WebInputElement.class)) {
			return AutoWebType.input;
		}
		else if(e.equals(WebSelectElement.class)) {
			return AutoWebType.select;
		}
		else if(e.equals(WebLinkElement.class)) {
			return AutoWebType.link;
		}
		else if(e.equals(WebTableElement.class)) {
			return AutoWebType.table;
		}
		else if(e.equals(WebContainerElement.class)) {
			return AutoWebType.table;
		}
		else {
			return AutoWebType.other;
		}
	}
}
