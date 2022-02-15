<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label class="form-label">Логин</label>
            <input type="text" class="form-control" id="usernameInput" aria-describedby="usernameHelp" name="username" placeholder="Введите логин">
            <div id="one-love" class="form-text">お会いできて嬉しい</div>
        </div>
        <div class="mb-3">
            <label class="form-label">Пароль</label>
            <input type="password" class="form-control" id="passwordInput" name="password" placeholder="Введите пароль">
        </div>
        <#if isRegisterForm>
        <div class="mb-3">
            <label class="form-label">Пароль</label>
            <input type="password" class="form-control" id="passwordSecondInput" name="passwordSecond" placeholder="Подтвердите пароль">
        </div>
        </#if>
<#--        <div class="mb-3 form-check">-->
<#--            <input type="checkbox" class="form-check-input" id="exampleCheck1">-->
<#--            <label class="form-check-label" for="exampleCheck1">Check me out</label>-->
<#--        </div>-->
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary"><#if !isRegisterForm>Войти<#else >Подтвердить</#if></button>
        <#if !isRegisterForm><a href="/registration"><#if isRegisterForm>Заррегистрироваться<#else>Страница регистрации</#if></a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Выйти</button>
    </form>
</#macro>