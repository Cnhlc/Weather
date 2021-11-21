package bistu.ct.weather.ui;

import androidx.fragment.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/* xutiles加载网络数据的步骤
 * 1、申明整体模块
 * 2、执行网络请求操作
 */
public class BaseFragment extends Fragment implements Callback.CommonCallback<String> {

    public void loadData(String path){
        RequestParams params = new RequestParams(path);
        System.out.println(path+" **************");
        /*第一个是请求的网址，封装到params中，第二个是一个回调函数，通过接口实现*/
        x.http().get(params,this);
    }

    /**
     * 获取成功时，回调的接口
     * @param result
     */
    @Override
    public void onSuccess(String result) {

    }

    /**
     * 失败是回调的接口
     * @param ex
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    /**
     * 取消请求的调用的接口
     * @param cex
     */
    @Override
    public void onCancelled(CancelledException cex) {

    }

    /**
     * 请求完成时，回调的接口
     */
    @Override
    public void onFinished() {

    }
}
