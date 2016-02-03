package forest;

import java.awt.event.MouseEvent;
import java.awt.Point;
import mvc.Controller;

/**
 * フォレストコントローラ。
 */
public class ForestController extends Controller
{
	/**
	 * フォレストコントローラのコンストラクタ。
	 * 良好（2013年7月21日）
	 */
	public ForestController()
	{
		super();
	}
	
	/**
	 * マウスクリックした位置の木の要素（文字列）を出力する。
     * @param aMouseEvent マウスイベントのインスタンス
     * 良好（2013年7月21日）
	 */
	public void mouseClicked(MouseEvent aMouseEvent)
	{
        Point aPoint = aMouseEvent.getPoint();
		aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
		ForestModel aForestModel = (ForestModel)(this.model);
		aForestModel.mouseClicked(aPoint, aMouseEvent);
		return;
	}
    
    /**
	 * ダブルバッファのpicture画像を基に、木全体を全体を動かす。
     * @param aMouseEvent マウスイベントのインスタンス
     * 良好（2013年7月21日）
	 */
    public void mouseDragged(MouseEvent aMouseEvent)
	{
		super.mouseDragged(aMouseEvent);
		return;
	}
}
