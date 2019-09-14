<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="profileWrapper">
  <div class="content">
    <h1>${ userProfile.getUsername() }</h1>
    <h3>User since ${ userProfile.getCreatedAt() }</h3>
    <c:if test="${scores.size() == 0}">
    	<h3>High Scores: none</h3>
    </c:if>
    <c:if test="${scores.size() != 0}">
    	<table>
    		<thead>
    			<tr>
    				<td>
    					Game
    				</td>
    				<td>
    					Score
    				</td>
    			</tr>
    		</thead>
    		<tbody>
    			<c:forEach items="${ scores }" var="score">
    				<tr>
    					<td>
    						${ score.getGame() }
    					</td>
    					<td>
    						${ score.getScore() }
    					</td>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>
    </c:if>
  </div>
</div>