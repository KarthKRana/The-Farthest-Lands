#version 150

// =============================================================================
// Farthest Portal — vertex shader
// File: assets/thefarthestlands/shaders/core/rendertype_farthest_portal.vsh
//
// Turns each portal vertex into clip-space, then builds a projected UV
// (texProj0) so the fragment shader can sample the sky/star textures as a
// "window" instead of a normal block texture.
//
// You usually do not need to edit this file. Color, speed, and grayscale live
// in rendertype_farthest_portal.fsh.
// =============================================================================

#moj_import <projection.glsl>

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 texProj0;

void main() {
    // Clip-space position of this portal quad vertex.
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    // Projected UVs for sampling End sky/stars as a "window" in the fragment shader.
    texProj0 = projection_from_position(gl_Position);
}
